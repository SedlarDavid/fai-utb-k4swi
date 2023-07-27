// SPDX-License-Identifier: MIT
pragma solidity ^0.8.6;

contract Coin {
    mapping (address => uint) public balances;
    address immutable minter;

    event Transfer(address from, address to, uint amount);

    constructor() {
        minter = msg.sender;
    }

    function mint(address receiver, uint amount) public {
        require(msg.sender == minter, "");
        balances[receiver] += amount;
    }

    function send(address receiver, uint amount) public {
        require(amount <= balances[msg.sender], "Insufficient balance"); 

        balances[msg.sender] -= amount;
        balances[receiver] += amount;

        emit Transfer(msg.sender, receiver, amount);
    }
  }
