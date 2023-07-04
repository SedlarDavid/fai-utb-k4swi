using System;
using UnityEngine;


public class Health : MonoBehaviour
{
    [SerializeField] private float startingHealth;
    public float currentHealth { get; private set; }

    private bool _dead;

    private Animator _animator;

    private void Awake()
    {
        currentHealth = startingHealth;
        _animator = GetComponent<Animator>();
    }

    public void TakeDamage(float _damage)
    {
        currentHealth = Mathf.Clamp(currentHealth - _damage, 0, startingHealth);

        if (currentHealth > 0)
        {
            _animator.SetTrigger("hurt");
        }
        else
        {
            if (!_dead)
            {
                _animator.SetTrigger("die");
                GetComponent<PlayerMovement>().enabled = false;
                _dead = true;
            }
        }
    }

    public void AddHealth(float _value)
    {
        currentHealth = Mathf.Clamp(currentHealth + _value, 0, startingHealth);
    }
}