#!/bin/bash

for f in DailyTests/day*
do
	>newusers.txt
	>newtickets.txt
	>MergedTransactions.txt
	sh daily.sh ./TicketWorld/TicketWorld ${f} transactions.txt
	cd dist
	java Processor ./../users.txt ./../tickets.txt ./../MergedTransactions.txt ./../newusers.txt ./../newtickets.txt
	cd ..
	mv newusers.txt users.txt
	mv newtickets.txt tickets.txt
done

echo "Backend has been run"
