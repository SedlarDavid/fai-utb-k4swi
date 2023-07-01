using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerAttack : MonoBehaviour
{
    [SerializeField] private float _attackCooldown;
    private Animator _animator;
    private PlayerMovement _playerMovement;
    private float _cooldownTimer = Mathf.Infinity;


    private void Awake()
    {
        _animator = GetComponent<Animator>();
        _playerMovement = GetComponent<PlayerMovement>();
    }

    private void Update()
    {
        if (Input.GetKey(KeyCode.LeftControl) && _cooldownTimer > _attackCooldown && _playerMovement.CanAttack())
        {
            Attack();
        }

        _cooldownTimer += Time.deltaTime;
    }

    private void Attack()
    {
        _animator.SetTrigger("attack");
        _cooldownTimer = 0;
    }
}