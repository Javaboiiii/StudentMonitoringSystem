import nodemailer from 'nodemailer';
import Mailgen from 'mailgen';

const sendMail = async (req, res) => { 
    const userEmail = 'devrajlokhande71@gmail.com';

    let config = { 
        service: 'gmail', 
        auth: { 
            user: process.env.user, 
            pass: process.env.pass
        }
    }

    let transporter = nodemailer.createTransport(config)

    let MailGenerator = new Mailgen({ 
        theme: 'default', 
        product: {
            name: 'Student Monitoring', 
            link: 'https://mailgen.js/',
            copyright : 'Copyright © 2024 Student Monitoring'
        }
    })

    let response = { 
        body: {
            name : "Assignment Pending ⚠⚠",
            intro: "Your assignment is Pending!!",
            table : {
                data : [
                    {
                        CourseName: 'Course Name', 
                        AssignmentName: 'Assignment Name'
                    }
                ]
            },
            outro: "Hope you submit assignment on time🕓🕓"
        }
    }

    let mail = MailGenerator.generate(response)

    let message = {
        from : process.env.user,
        to : userEmail,
        subject: "Assignment Pending",
        html: mail
    }

    transporter.sendMail(message).then(() => {
        return console.log('Email sent')
    }).catch(error => {
        return console.log(error)
    })

}

sendMail();