using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    private Rigidbody2D _body;
    [SerializeField] private float speed;

    private Animator _anim;

    private bool _grounded;

    private void Awake()
    {
        _body = GetComponent<Rigidbody2D>();
        _anim = GetComponent<Animator>();
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

        if ((Input.GetKey(KeyCode.Space) || Input.GetKey(KeyCode.UpArrow)) & _grounded)
        {
            Jump();
        }

        _anim.SetBool("run", horizontalInput != 0);
        _anim.SetBool("grounded", _grounded);
    }

    private void Jump()
    {
        _body.velocity = new Vector2(_body.velocity.x, speed * 0.75f);
        _anim.SetTrigger("jump");
        _grounded = false;
    }

    private void OnCollisionEnter2D(Collision2D other)
    {
        if (other.gameObject.tag == "Ground")
        {
            _grounded = true;
        }
    }
}