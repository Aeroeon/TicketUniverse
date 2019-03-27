#!/bin/bash

program=${1}
testPath=${2}
transactions=${3}

>MergedTransactions.txt
mergedTransactions="MergedTransactions.txt"

for f in ${testPath}/*.input
do
	${program} < ${f} >> "output.txt"
	cat ${transactions} >> ${mergedTransactions}
done

echo "00" >> ${transactions}
echo "Simulation finished, transactions file has been merged."
