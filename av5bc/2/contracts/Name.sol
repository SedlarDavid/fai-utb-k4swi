// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.6;

contract Name {
    string myName;

    event nameChanged(string name);

    function getMyName() public view returns (string memory) {
        return myName;
    }

    function changeMyName(string memory _newName) public {
        myName = _newName;
	emit nameChanged(_newName); 
    }
}
