using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    [SerializeField] private float speed;
    [SerializeField] private float jumpPower;
    [SerializeField] private LayerMask groundLayer;
    [SerializeField] private LayerMask wallLayer;

    [Header("SFX")] [SerializeField] private AudioClip _jumpSound;

    private Rigidbody2D _body;
    private Animator _anim;
    private BoxCollider2D _boxCollider;
    private float _wallJumpCooldown;
    private float _horizontalInput;


    private void Awake()
    {
        _body = GetComponent<Rigidbody2D>();
        _anim = GetComponent<Animator>();
        _boxCollider = GetComponent<BoxCollider2D>();
    }

    private void Update()
    {
        _horizontalInput = Input.GetAxis("Horizontal");

        var scaleTransform = transform;
        scaleTransform.localScale = _horizontalInput switch
        {
            //Flip to the right
            > 0.01f => new Vector3(4, 4, 4),
            //Flip to the left
            < -0.01f => new Vector3(-4, 4, 4),
            _ => scaleTransform.localScale
        };

        //Set animator params
        _anim.SetBool("run", _horizontalInput != 0);
        _anim.SetBool("grounded", IsGrounded());


        //Wall jump logic
        if (_wallJumpCooldown > 0.2f)
        {
            _body.velocity = new Vector2(_horizontalInput * speed, _body.velocity.y);

            if (OnWall() && !IsGrounded())
            {
                _body.gravityScale = 0;
                _body.velocity = Vector2.zero;
            }
            else
            {
                _body.gravityScale = 7;
            }

            if (IsJumpKey())
            {
                Jump();
                if ((Input.GetKeyDown(KeyCode.Space) || Input.GetKeyDown(KeyCode.UpArrow)) && IsGrounded())
                {
                    SoundManager.instance.PlaySound(_jumpSound);
                }
            }
        }
        else
        {
            _wallJumpCooldown += Time.deltaTime;
        }
    }


    private void Jump()
    {
        if (IsGrounded())
        {
            _body.velocity = new Vector2(_body.velocity.x, jumpPower);
            _anim.SetTrigger("jump");
        }
        else if (OnWall() && !IsGrounded())
        {
            if (_horizontalInput == 0)
            {
                _body.velocity = new Vector2(-Mathf.Sign(transform.localScale.x) * 10, 0);
                transform.localScale = new Vector3(-Mathf.Sign(transform.localScale.x), transform.localScale.y,
                    transform.localScale.z);
            }
            else
            {
                _body.velocity = new Vector2(-Mathf.Sign(transform.localScale.x) * 3, 6);
            }

            _wallJumpCooldown = 0;
        }
    }


    private bool IsGrounded()
    {
        // BoxCast will create virtual box underneath the player, if the ray from that box collides from specified layer we can 
        // settle up some actions
        var raycastHit =
            Physics2D.BoxCast(_boxCollider.bounds.center, _boxCollider.bounds.size, 0, Vector2.down, 0.1f, groundLayer);
        return raycastHit.collider != null;
    }

    private bool OnWall()
    {
        // BoxCast will create virtual box underneath the player, if the ray from that box collides from specified layer we can 
        // settle up some actions
        var raycastHit =
            Physics2D.BoxCast(_boxCollider.bounds.center, _boxCollider.bounds.size, 0,
                new Vector2(transform.localScale.x, 0), 0.1f, wallLayer);
        return raycastHit.collider != null;
    }


    private static bool IsJumpKey()
    {
        return (Input.GetKey(KeyCode.Space) || Input.GetKey(KeyCode.UpArrow));
    }

    public bool CanAttack()
    {
        return _horizontalInput == 0 && IsGrounded() && !OnWall();
    }
}