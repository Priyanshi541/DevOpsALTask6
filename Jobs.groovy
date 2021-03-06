job("task6_job1"){
    description("Downloading the files from GitHub And Creating a Docker Image using the files")
	scm{
		github('priyanshi541/DevOpsALTask6' , 'master')
                       }
	triggers{
		scm(" * * * * * ")
	             }
	steps{
		shell('''if ls / | grep task6_dev
                                then
                                echo "Folder already created"
                                sudo cp * /task6_dev
                                else
                                sudo mkdir /task6_dev
                                sudo cp * /task6_dev
                                fi

                                cd /task6_dev/
				
                                if cat Dockerfile | grep *.html
				then
					if sudo kubectl get deployment myapp
					then
					echo "Deployment Exists"
					else
					sudo kubectl create -f /task6_dev/deploy.yml
				fi
				else 
				echo "Not an html website"
				fi
                                ''')
	}
}



job("task6_job2"){
    description("Testing the Application that it is properly deployed or not")
	triggers{
		upstream('task6_job1' , 'SUCCESS')
	}
	steps{
		shell('''if [[ $(curl -o /dev/null  -s  -w "%{http_code}"  http://192.168.43.138:30303) == 200 ]]
		then
		echo "Application Running"
		else
		echo "There is some problem with the application"
		fi
		''')
	}
}


job("task6_job3"){
    description("Sending a Mail to a Developer if the Application Fails")
	triggers{
		upstream('task6_job2' , 'SUCCESS')
	}
	steps{
 		shell('''if [[ $(curl -o /dev/null  -s  -w "%{http_code}"  http://192.168.43.138:30303) == 200 ]]
		then
		echo " App is Properly Running"
		else
 		shell("python3 /task6_dev/mail.py")
		fi	
		''')
	}
}
