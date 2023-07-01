using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    [SerializeField] private float speed;
    [SerializeField] private LayerMask groundLayer;

    private Rigidbody2D _body;
    private Animator _anim;
    private BoxCollider2D _boxCollider;


    private void Awake()
    {
        _body = GetComponent<Rigidbody2D>();
        _anim = GetComponent<Animator>();
        _boxCollider = GetComponent<BoxCollider2D>();
    }

    private void Update()
    {
        var horizontalInput = Input.GetAxis("Horizontal");
        _body.velocity = new Vector2(horizontalInput * speed, _body.velocity.y);

        var scaleTransform = transform;
        scaleTransform.localScale = horizontalInput switch
        {
            //Flip to the right
            > 0.01f => new Vector3(4, 4, 4),
            //Flip to the left
            < -0.01f => new Vector3(-4, 4, 4),
            _ => scaleTransform.localScale
        };

        if ((Input.GetKey(KeyCode.Space) || Input.GetKey(KeyCode.UpArrow)) & IsGrounded())
        {
            Jump();
        }

        _anim.SetBool("run", horizontalInput != 0);
        _anim.SetBool("grounded", IsGrounded());
    }

    private void Jump()
    {
        _body.velocity = new Vector2(_body.velocity.x, speed );
        _anim.SetTrigger("jump");
    }


    private bool IsGrounded()
    {
        // BoxCast will create virtual box underneath the player, if the ray from that box collides from specified layer we can 
        // settle up some actions
        var raycastHit =
            Physics2D.BoxCast(_boxCollider.bounds.center, _boxCollider.bounds.size, 0, Vector2.down, 0.1f, groundLayer);
        return raycastHit.collider != null;
    }
}