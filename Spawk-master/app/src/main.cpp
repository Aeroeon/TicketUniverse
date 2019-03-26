#include <stdlib.h>
#include <iostream>
#include "./components/App.cpp"


int main(int argc, char **argv)
{
  App app;
  string cmd;
  unordered_map<string, string> ticketData;
  Ticket ticket;
  std::cout << "Welcome To Spawk!\n\n";


  ticketData["title"] = "Test Title";
  ticketData["seller"] = "Pierre";
  ticketData["price"] = "20";
  ticketData["qty"] = "2";
  ticketData["type"] = "AA";

  string title = ticketData["title"];
  string seller = ticketData["seller"];
  float price = stof(ticketData["price"]);
  int qty = stoi(ticketData["qty"]);

  ticket.setEventTitle(title);
  ticket.setSeller(seller);
  ticket.setPrice(price);
  ticket.setQuantity(qty);
  ticket.setCanBeBought(true);

  // cout << "ticket: EventTitle -> " << ticket.getEventTitle() << "\n\n";
  // cout << "ticket: Seller -> " << ticket.getSeller() << "\n\n";
  // cout << "ticket: Price -> " << ticket.getPrice() << "\n\n";
  // cout << "ticket: QTY -> " << ticket.getQuantity() << "\n\n";
  // cout << "ticket: CanBeBought -> " << ticket.getCanBeBought() << "\n\n";

  app.updateTickets(ticket);
  ticket = app.getTicket(ticketData);
  
  
  std::cout << ticket.getEventTitle() << "\n";
  ticket.setEventTitle("upadate");
  app.updateTickets(ticket);
  app.updateTickets(ticket);
  // cout << ticket.getEventTitle() << "\n\n";

  // cout << ticket.getQuantity() << "\n";
  ticketData["qty"] = "1";
  app.buyTickets(ticketData);
  std::cout << app.getTicket(ticketData).getQuantity() << "\n\n";
  
  ticketData["qty"] = "3";
  app.sellTickets(ticketData);
  std::cout << app.getTicket(ticketData).getQuantity() << "\n\n";

  do {
    if (cmd != "exit")
		std::cout << "\n\nPlease Login: ";
	std::cin >> cmd;
  } while (cmd != "exit");

  return 0;
}