#!/bin/bash 

function usage() 
{
    echo " Your input is wrong!"
    
    exit 0
}

    case $1 in
    deploy)
            [ $# -lt 1 ] && { usage; }
            ;;
    createCompany)
            [ $# -lt 5 ] && { usage; }
            ;;
    createReceipt)
            [ $# -lt 5 ] && { usage; }
            ;;
    transferReceipt)
            [ $# -lt 4 ] && { usage; }
            ;;
    borrowFromBank)
            [ $# -lt 3 ] && { usage; }
            ;;
    endReceipt)
            [ $# -lt 3 ] && { usage; }
            ;;
    totalMoney)
            [ $# -lt 2 ] && { usage; }
            ;;
    fromReceipt)
            [ $# -lt 2 ] && { usage; }
            ;;
    toReceipt)
            [ $# -lt 2 ] && { usage; }
            ;;
    *)
        usage
            ;;
    esac

    java -Djdk.tls.namedGroups="secp256k1" -cp 'apps/*:conf/:lib/*' org.fisco.bcos.asset.client.ProjectClient $@

