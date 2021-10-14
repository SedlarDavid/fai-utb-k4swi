// SPDX-License-Identifier: MIT
pragma solidity ^0.8.6;

struct FullName {
    string firstName;
    string lastName;
    uint256 age;
}

contract Solidity {
    address public owner = payable(msg.sender);
    uint256 public last_completed_migration;
    bool public isActive = true;
    string name = "My panic Contract";
    string special = unicode"ščš";
    string[] users;
    mapping(address => FullName) usersMap;
    int256 immutable user;

    bool isRunning = false;

    constructor() {
        user = 1;
    }

    enum Color {
        Red,
        Green,
        Blue
    }

    event ContractStarted(uint256 time);

    //function start () public pure {}  // pure => don't have access to storage
    function start(string calldata _name) public {
        require(msg.sender != owner, "Sender can't be owner!");
        isRunning = true;

        emit ContractStarted(block.timestamp);

        require(block.timestamp > 122225555, "Wait until 122225555");

        bool isEqual = keccak256(bytes(name)) == keccak256(bytes(_name));
        name = _name;
        string memory secondName = _name;

        owner = 0xc518b07Ca0Da84d1E038604D7e8f6E0030f267C5;
        //If address is payable => owner.call{value:1}("");

        int256 number = 10;
        //Mostly just uint bcs we don't need sign (+-)

        Color redColor = Color.Red;
        if (redColor == Color.Red) {}

        users.push(_name);
        string memory firstUser = users[0];

        FullName memory fullName = FullName("David", "S", 23);

        usersMap[msg.sender] = fullName;

        //Exits contract and rollback changes => revert();
    }

    function getMyName() public view returns (FullName memory fullName) {
        return (usersMap[msg.sender]);
    }
}
