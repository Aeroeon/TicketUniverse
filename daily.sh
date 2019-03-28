#!/bin/bash

program=${1}
testPath=${2}
transactions=${3}

>output.txt
mergedTransactions="MergedTransactions.txt"

for f in ${testPath}/*.txt
do
	>${transactions}
	${program} < ${f} >> "output.txt"
	cat ${transactions} >> ${mergedTransactions}
done

echo "00" >> ${mergedTransactions}
echo "Simulation finished, transactions file has been merged."
