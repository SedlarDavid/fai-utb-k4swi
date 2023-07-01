using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerAttack : MonoBehaviour
{
    [SerializeField] private float attackCooldown;
    [SerializeField] private Transform firePoint;
    [SerializeField] private GameObject[] boots;

    private Animator _anim;
    private PlayerMovement _playerMovement;
    private float _cooldownTimer = Mathf.Infinity;

    private void Awake()
    {
        _anim = GetComponent<Animator>();
        _playerMovement = GetComponent<PlayerMovement>();
    }

    private void Update()
    {
        if ((Input.GetKey(KeyCode.LeftControl) || Input.GetKey(KeyCode.RightControl)) &&
            _cooldownTimer > attackCooldown && _playerMovement.CanAttack())
            Attack();

        _cooldownTimer += Time.deltaTime;
    }

    private void Attack()
    {
        _anim.SetTrigger("attack");
        _cooldownTimer = 0;

        boots[FindFireball()].transform.position = firePoint.position;
        boots[FindFireball()].GetComponent<Projectile>().SetDirection(Mathf.Sign(transform.localScale.x));
    }

    private int FindFireball()
    {
        for (int i = 0; i < boots.Length; i++)
        {
            if (!boots[i].activeInHierarchy)
                return i;
        }

        return 0;
    }
}