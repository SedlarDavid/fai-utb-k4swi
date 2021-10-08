#1
- Vytvoreni noveho accountu
    'geth account new --datadir local_chain'

- Zapsat public adresu do genesis => alloc

- Initilizace 
    'geth init genesis.json --datadir local_chain'

- Mining
    'geth --datadir local_chain --nodiscover --mine --miner.threads 1 --allow-insecure-unlock --http --http.api eth,web3,personal,net --miner.etherbase=0xc518b07Ca0Da84d1E038604D7e8f6E0030f267C5'


#2
- Kompilace sol
    'cd 2'
    'solcjs -o bin --bin --abi .\Name.sol'

- Attach na blockhain
    'geth attach http://127.0.0.1:8545'
    'hex = "0x_binarka_" '
    'abi = _abiJson_ '  
    'Name = eth.contract(abi)'
    'personal.unlockAccount(eth.accounts[0])'
    'tx = Name.new({from:eth.accounts[0], data: hex})'
    'addr = tx.address'
    'Name.at(addr).getMyName.call()'
    'Name.at(addr).changeMyName("David", {from: eth.accounts[0]})'

- Init pres truffle
    'truffle init'
    Prepsat migrations na Name 
    Zmenit v truffle config compile solc na pragma v solu
    'truffle deploy'
    'truffle console'
    'const instance = await Name.deployed()'
    'instance.getMyName()'
    'instance.changeMyName("Dejw")'







































N3wG3thAccount