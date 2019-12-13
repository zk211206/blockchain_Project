pragma solidity ^0.4.22;

contract Project{

    event TotalMoneyEvent(uint ret,string f);
    event FromReceiptEvent(uint ret,string f);
    event ToReceiptEvent(uint ret,string f);
    
    struct company{
        string name;
        string add;
        string category;
        uint totalMoney;
        
    }
    
    struct receipt{
        string fromCompany;
        string toCompany;
        uint money;
        string status;
        
    }
    
    company[] Companies;
    receipt[] Receipts;
    
    function createCompany(string n,string a,string c,uint t) public{
        Companies.push(company(n,a,c,t));
    }
    
    function createReceipt(string f,string t,uint m,string s){
        Receipts.push(receipt(f,t,m,s));
    }
    
    function transferReceipt(string creator,string t,uint m){
        uint i;
        uint total=0;
        for(i=0;i<Receipts.length;i++){
            if(keccak256(Receipts[i].toCompany)==keccak256(creator)){
                total=total+Receipts[i].money;
            }
        }
        if(total>=m){
            uint j;
            uint one;
            for(j=0;j<Receipts.length;j++){
                if(keccak256(Receipts[j].toCompany)==keccak256(creator)){
                    one=Receipts[j].money;
                    if(one<m){
                        m=m-one;
                        Receipts[j].money=0;
                        createReceipt(Receipts[j].fromCompany,t,one,"believe");
                    }
                    else{
                        Receipts[j].money=Receipts[j].money-m;
                        createReceipt(Receipts[j].fromCompany,t,m,"believe");
                        break;
                    }
                }
            }
        }
    }
    
    function borrowFromBank(string creator,uint m){
        uint i;
        uint total=0;
        for(i=0;i<Receipts.length;i++){
            if(keccak256(Receipts[i].toCompany)==keccak256(creator)){
                total=total+Receipts[i].money;
            }
        }
        if(total>=m){
            uint j;
            for(j=0;j<Companies.length;j++){
                if(keccak256(Companies[j].name)==keccak256(creator)){
                    Companies[j].totalMoney=Companies[j].totalMoney+m;
                }
            }
        }
    }
    
    function endReceipt(string f,string t){
        uint i;
        for(i=0;i<Receipts.length;i++){
            if(keccak256(Receipts[i].fromCompany)==keccak256(f)&&keccak256(Receipts[i].toCompany)==keccak256(t)){
                uint j;
                for(j=0;j<Companies.length;j++){
                    if(keccak256(Companies[j].name)==keccak256(f)){
                        Companies[j].totalMoney=Companies[j].totalMoney-Receipts[i].money;
                    }
                    if(keccak256(Companies[j].name)==keccak256(t)){
                        Companies[j].totalMoney=Companies[j].totalMoney+Receipts[i].money;
                    }
                }
                Receipts[i].money=0;
            }
        }
    }
    
    function totalMoney(string f) returns(uint){
        uint i;
        for(i=0;i<Companies.length;i++){
            if(keccak256(Companies[i].name)==keccak256(f)){
                emit TotalMoneyEvent(Companies[i].totalMoney,f);
                return Companies[i].totalMoney;
            }
        }
    }
    
    function fromReceipt(string f) returns(uint){
        uint i;
        uint total=0;
        for(i=0;i<Receipts.length;i++){
            if(keccak256(Receipts[i].fromCompany)==keccak256(f)){
                total=total+Receipts[i].money;
            }
        }
        emit FromReceiptEvent(total,f);
        return total;
    }
    
    function toReceipt(string f) returns(uint){
        uint i;
        uint total=0;
        for(i=0;i<Receipts.length;i++){
            if(keccak256(Receipts[i].toCompany)==keccak256(f)){
                total=total+Receipts[i].money;
            }
        }
        emit ToReceiptEvent(total,f);
        return total;
    }
    
}
