#!/bin/bash

> DailyTests/MergedTransactions.txt
mergedTransactions="DailyTests/MergedTransactions.txt"
i=0
while [[ ${i} -lt 2 ]];
do
	let i=i+1;
	echo "Starting test Test$i"
	./TicketWorld < "DailyTests/test${i}.txt" > "DailyTests/output${i}.txt" &
	echo "Test$i finished"
	cat transactions.txt >> ${mergedTransactions}
	echo "Merged transaction files"
done

echo "00" >> ${mergedTransactions}
