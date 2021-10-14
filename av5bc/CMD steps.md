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

#3
 - truffle init
 ... vytvareni solidity souboru ... 
 Prepsat correct verzi v compilers
 Prepsat migraci na contract ktery chceme deployovat
 - truffle compile
 Start Ganache
 Link projects with Ganache
 - truffle deploy
 - truffle console
 - coin = await Coin.deployed()
 - coin.mint("0x16387c825f82B90B4128465Ca4dd485b58594b20",10)  
 - coin.send("0x12fBf72B9F68FFec7bec617cFca079b5216895fE",5,{from:"0x16387c825f82B90B4128465Ca4dd485b58594b20"})       

Tomáš Janča 
    - Zlínský kraj, digitalizace
    - StarGlass
    - Rockaway blockchain fondD
    - předmět - Forbes, Rockaway, Datum (Brno) startup, Akee blockchain
    - Akee udělá zadání, bude vyhodnocovat  100k, 75k, 50k, individuální
    - BC dev 200-250k, Akee 700k/m ???
    - Solana !, Rust lang




































N3wG3thAccount