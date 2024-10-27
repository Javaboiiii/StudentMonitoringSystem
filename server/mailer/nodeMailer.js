import nodemailer from 'nodemailer';
import Mailgen from 'mailgen';

const sendMail = async (email, title) => { 
    const senderEmail = email;

    let config = { 
        service: 'gmail', 
        auth: { 
            user: 'devrajlokhande1610@gmail.com', 
            pass: 'vdccqigshssgkhpm'
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
                        AssignmentName: title
                    }
                ]
            },
            outro: "Hope you submit assignment on time🕓🕓"
        }
    }

    let mail = MailGenerator.generate(response)

    let message = {
        from : 'devrajlokhande1610@gmail.com',
        to : senderEmail,
        subject: "Assignment Pending",
        html: mail
    }

    transporter.sendMail(message).then(() => {
        return console.log('Email sent')
    }).catch(error => {
        return console.log(error)
    })

}

export default sendMail; 