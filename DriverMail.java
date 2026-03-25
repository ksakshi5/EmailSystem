import java.util.ArrayList;
import java.util.Scanner;
import java.time.*;

class Mail
{
	private LocalDate date ;
	private LocalTime time ;
	private String from ;
	private String to ;
	private String subject ; 
	private String desc ;

	Mail(LocalDate date, LocalTime time, String from, String to, String subject, String desc)
	{
		super();
		this.date = date ;
		this.time = time ;
		this.from = from ;
		this.to = to ;
		this.subject = subject ;
		this.desc = desc ;
	}

	public void displayMail()
	{

		System.out.println("\n-------------------------------------");
		System.out.println("Sakshi Mail | Time : "+date+" "+time.getHour() +":"+time.getMinute()+":"+time.getSecond());
		System.out.println("From : "+from);
		System.out.println("To : "+to);
		System.out.println("Subject : "+subject);
		System.out.println("Description : "+desc);
		System.out.println("---------------------------------------");
	}

	public String getTo()
	{
		return this.to;
	}

	public void setTo(String newTo)
	{
		this.to = newTo;
	}
	public void setSubject(String newSubject)
	{
		this.subject = newSubject;
	}
	public void setDesc(String newDesc)
	{
		this.desc = newDesc;
	}
}


class User 
{
	private String username ;
	private String email ;
	private String password ;
	private String dob ;
	private long contact ;

	private ArrayList<Mail> inbox = new ArrayList<Mail>();
	private ArrayList<Mail> send = new ArrayList<Mail>();
	private ArrayList<Mail> draft = new ArrayList<Mail>();
	private ArrayList<Mail> starred = new ArrayList<Mail>();
	private ArrayList<Mail> bin = new ArrayList<Mail>();


	User(String username, String email, String password, String dob, long contact)
	{
		super();
		this.username = username ;
		this.email = email ;
		this.password = password ;
		this.dob = dob ;
		this.contact = contact ;
	}


	public String getEmail()
	{
		return this.email;
	}
	public String getPassword()
	{
		return this.password;
	}
	public ArrayList<Mail> getInbox()
	{
		return this.inbox;
	}
	public ArrayList<Mail> getSendBox()
	{
		return this.send;
	}
	public ArrayList<Mail> getDraft()
	{
		return this.draft;
	}
}


class Gmail
{
	private ArrayList<User> listUser = new ArrayList<User>();

	public void launchApp()
	{
		for(;;)
		{
			System.out.println("\n************ WELCOME **************\n");
			System.out.println("========== SAKSHI MAIL SYSTEM =========");
			System.out.println("1.Existing user \n2.Create new Account");
			
			System.out.print("Enter your option : ");
			int opt = new Scanner(System.in).nextInt();
			switch(opt) 
			{
				case 1 -> login();
				case 2 -> createAccount();
				default -> System.out.println("\n------------INVALID OPTION------------- \n"); 
			}
		}
	}

	private void login()
	{
		if(listUser.isEmpty())
		{
			System.out.println("\n-----------NO USERS FOUND----------\n");
			return;
		}

		System.out.println("\n*-*-*-*-*-* LOGIN *-*-*-*-*-*\n");
		System.out.print("Email : ");
		String email = new Scanner(System.in).next();
		if(!(email.endsWith("@gmail.com")))
			email += "@gmail.com";

		System.out.print("Password : ");
		String password = new Scanner(System.in).next();
		boolean assump = false ;
		for(User obj : listUser)
		{
			String email1 = obj.getEmail();
			String password1 = obj.getPassword();
			if(email.equals(email1) && password.equals(password1))
			{
				assump = true;
				System.out.println("\n*-*-*-*-*-*-* LOGIN SUCCESSFUL *-*-*-*-*-*-*-*-*\n");
				features(obj);
			} 

			if(!assump)
				System.out.println("\n-----------INVALID INFORMATION----------\n");
		}
	}
	private void createAccount()
	{
		System.out.println("\n*-*-*-*-*-*-*-*-* ACCOUNT CREATION MODULE *-*-*-*-*-*-*-*-\n");
		System.out.print("First name : ");
		String firstName = new Scanner(System.in).next();
		System.out.print("Last Name : ");
		String lastName = new Scanner(System.in).next();

		String username = firstName + " " + lastName;
		String email = "";
		
		emailCheck:
		for(;;)
		{
			System.out.print("Email : ");
			email = new Scanner(System.in).next();
			boolean cond = checkUserEmail(email);
			if(cond)
			{
				System.out.println("\n-------------USERNAME ALREADY TAKEN------------\n");
				System.out.println("# Suggestions ");
				String[] arr = suggestion(firstName);
				int srno = 1;
				for(String sugg : arr)
				{
					System.out.println(srno++ +" : "+ sugg);
				}
				System.out.print("\nDo You Want User Suggestion : ");
				String resp = new Scanner(System.in).next().toUpperCase();
				if(resp.equals("YES"))
				{
					System.out.print("Enter your option : ");
					int suggOpt = new Scanner(System.in).nextInt();
					if(suggOpt == 1)
					{
						email = arr[0] + "@gmail.com";
						break emailCheck;
					}
					else if(suggOpt == 2)
					{
						email = arr[1] + "@gmail.com";
						break emailCheck;
					}
					else if(suggOpt == 3)
					{
						email = arr[2] + "@gmail.com";
						break emailCheck;
					}
					else 
						System.out.println("-----------INVALID OPTION-------------");
				}
				else
				{
					continue emailCheck;
				}
			}
			else 
			{
				break emailCheck;
			}
		}

		if(!email.endsWith("@gmail.com"))
			email += "@gmail.com";
		System.out.print("Password : ");
		String password = new Scanner(System.in).next();
		System.out.print("Date of Birth : ");
		String dob = new Scanner(System.in).next();
		System.out.print("Contact : ");
		long contact = new Scanner(System.in).nextLong();

		User user = new User(username,email,password,dob,contact);
		listUser.add(user);
		System.out.println("\n*-*-*-*-*-*-*-*-* USER ACCOUNT CREATED SUCCESSFULLY *-*-*-*-*-*-*\n");
	}


	private String[] suggestion(String fisrtName)
	{
		String[] arr = new String[3];
		// [null, null, null]
		//   0     1     2

		int index = 0;
		for(int count=1; count<=3;)
		{
			String sugg = fisrtName+(int)(Math.random()*1000);
			boolean cnd = checkUserEmail(sugg);
			if(!cnd)
			{
				arr[index++] = sugg;
				count++;
			}
		}
		return arr;
	}

	private boolean checkUserEmail(String email)
	{
		for(User obj : listUser)
		{
			if(email.equals(obj.getEmail()))
				return true;
		}
		return false;
	}

	private void features(User curr)
	{
		for(;;)
		{
			System.out.println("\n*************FEATURES *************\n");
			System.out.println("1. Compose");
			System.out.println("2. Send mail");
			System.out.println("3. Inbox");
			System.out.println("4. Draft");
			System.out.println("5. Starred mail");
			System.out.println("6. Bin");
			System.out.println("7. Logout");

			System.out.print("Enter your Option : ");
			int option = new Scanner(System.in).nextInt();
			switch (option) 
			{
				case 1 -> composeMail(curr);
				case 2 -> sendMail(curr);
				case 3 -> inboxMail(curr);
				case 4 -> draft(curr); 
				case 5 -> {logout(curr); return;}
			}
		}
	}

	private void logout(User curr)
	{
		System.out.println();
		System.out.println(curr.getEmail() + " Thank You !!!");
		System.out.println("LOGGED OUT SUCCESSFULLY !!!");
		launchApp();
	}

	private void sendMail(User curr)
	{
		if(curr.getSendBox().isEmpty())
		{
			System.out.println("\n-------------NO MAIL-----------\n");
			return;
		}
		System.out.println("\n*-*-*-*-*-*-*-*-* SEND MAIL *-*-*-*-*-*-*-*\n");
		ArrayList<Mail> send = curr.getSendBox();
		for(Mail obj : send)
			obj.displayMail();
	}

	private void inboxMail(User curr)
	{
		if(curr.getInbox().isEmpty())
		{
			System.out.println("\n--------------NO MAIL-----------\n");
			return;
		}
		System.out.println("\n*-*-*-*-*-*-*-*- INBOX MAIL *-*-*-*-*-*-*-*-*-*\n");
		ArrayList<Mail> inbox = curr.getInbox();
		for(Mail obj : inbox)
			obj.displayMail();
	}

	private void draft(User curr)
	{
		if(curr.getDraft().isEmpty())
		{
			System.out.println("\n------------NO MAIL-------------\n");
			return;
		}
		System.out.println("\n*-*-*-*-*-*-*-*-* DRAFT MAIL *-*-*-*-*-*-*-*-*\n");
		int cnt = 1;
		ArrayList<Mail> draft = curr.getDraft();
		for(Mail obj : draft)
		{
			System.out.println("Sr No : "+ cnt++);
			obj.displayMail();
		}
		System.out.print("DO YOU WANT TO EDIT A MAIL(draft) : ");
		String resp = new Scanner(System.in).next().toUpperCase();
		if(resp.equals("YES"))
		{
			System.out.print("\nEnter SR NO : ");
			int srNo = new Scanner(System.in).nextInt();
			Mail edit = curr.getDraft().get(srNo-1);
			editDraft(edit);
			System.out.print("\nDO YOU WANT TO SEND : ");
			String resp1 = new Scanner(System.in).next().toUpperCase();

			if(resp1.equals("YES"))
			{
				User receiver = fetchReceiverObj(edit.getTo());
				receiver.getInbox().add(edit);
				curr.getSendBox().add(edit);
				curr.getDraft().remove(srNo-1);
			}
			else
			{
				curr.getDraft().add(edit);
			}
		}
		else
		{
			System.out.print("\nDO YOU WANT TO SEND : ");
			String resp1 = new Scanner(System.in).next().toUpperCase();
			if(resp1.equals("YES"))
			{
				System.out.print("\nEnter SrNo : ");
				int srNo = new Scanner(System.in).nextInt();
				Mail edit = curr.getDraft().get(srNo-1);
				User receiver = fetchReceiverObj(edit.getTo());
				receiver.getInbox().add(edit);
				curr.getSendBox().add(edit);
				curr.getDraft().remove(srNo-1);
			}
		}
	}


	private void editDraft(Mail edit)
	{
		System.out.println("\n*-*-*-*-*-*-*-* EDIT DRAFT *-*-*-*-*-*-*-*\n");
		System.out.println("1. To");
		System.out.println("2. Subject");
		System.out.println("3. Description");
		System.out.print("Enter your option : ");
		int op = new Scanner(System.in).nextInt();
		switch(op) 
		{
			case 1 -> {
						String toMail = " ";
						for(;;)
						{
							System.out.print("TO : ");
							toMail = new Scanner(System.in).next();
							boolean cnd = checkUserEmail(toMail);
							if(cnd)
								break;
							else 
							{
								System.out.println("\n------------INVALID EMAIL------------\n");
								continue;
							}
						}
						edit.setTo(toMail);
						break;
					  }
			case 2 -> {
						String newSubject = new Scanner(System.in).nextLine();
						edit.setSubject(newSubject);
						break;
					  }
			case 3 -> {
						String newDesc = new Scanner(System.in).nextLine();
						edit.setDesc(newDesc);
						break;
					  }
			default -> System.out.println("\n----------INVALID INPUT----------\n");
		}
	}


	private void composeMail(User curr)
	{
		System.out.println("\n*-*-*-*-*-*-*-*-* COMPOSE MAIL *-*-*-*-*-*-*-*-*\n");
		String from = curr.getEmail();
		System.out.println("from : "+from);
		String to = "";
		for(;;)
		{
			System.out.print("TO : ");
			to  = new Scanner(System.in).next();
			boolean cnd = checkUserEmail(to);
			if(cnd)
				break;
			else 
			{
				System.out.println("\n----------INVALID EMAIL--------- \n");
				continue;
			}
		}
		
		System.out.print("Subject : ");
		String subject = new Scanner(System.in).nextLine();
		System.out.print("Description : ");
		String desc = new Scanner(System.in).nextLine();
		LocalDate d = LocalDate.now();
		LocalTime t = LocalTime.now();
		Mail mail = new Mail(d,t,from,to,subject,desc);

		System.out.print("\nDO YOU WANT TO SEND ? ");
		String resp = new Scanner(System.in).next().toUpperCase();
		if(resp.equals("YES"))
		{
			User receiver = fetchReceiverObj(to);
			receiver.getInbox().add(mail);
			curr.getSendBox().add(mail);
			System.out.println("*-*-*-*-*-*-*-*-* Mail Sent Successfully *-*-*-*-*-*-*-*-*");
		}
		else 
		{
			curr.getDraft().add(mail);
			System.out.println("---------Saved to Draft---------"); 
		}
	}

	private User fetchReceiverObj(String email)
	{
		for(User obj : listUser)
		{
			if(obj.getEmail().equals(email))
				return obj;
		}
		return null;
	}
}

class DriverMail
{
	public static void main(String[] args) 
	{
		new Gmail().launchApp();
	}
}