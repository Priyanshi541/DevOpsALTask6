f= open("/task6_dev/mail.py" ,"r")
a=f.read()
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
host_address = "*****8*@gmail.com"
host_pass = "*********"
guest_address = "***********@gmail.com"
subject = "Website Deployment Error"
content = "We would like to inform you that the website is not working... Please check your code";
message = MIMEMultipart()
message['From'] = host_address
message['To'] = guest_address
message['Subject'] = subject
message.attach(MIMEText(content, 'plain'))
session = smtplib.SMTP('smtp.gmail.com', 587)
session.starttls()
session.login(host_address, host_pass)
text = message.as_string()
session.sendmail(host_address, guest_address  , text)
session.quit()
print('Successfully sent your mail')