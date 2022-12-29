#include <QCoreApplication>
#include <QTimer>
#include <iostream>
#include "student.h"
#include "teacher.h"

void onTime(){
    std::cout << "Tick..." << std::endl;
}

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    Teacher teacherDavid;
    Student s1("Marek");
    Student s2("Vojta");
    Student s3("Adam");

    QObject::connect(&teacherDavid, &Teacher::teacherIncome,&s1, &Student::welcomeTeacher);
    QObject::connect(&teacherDavid, &Teacher::teacherIncome,&s2, &Student::welcomeTeacher);
    QObject::connect(&teacherDavid, &Teacher::teacherIncome,&s3, &Student::welcomeTeacher);

    QObject::connect(&s1, &Student::raiseHand,&teacherDavid, &Teacher::noticeStudent);
    QObject::connect(&s2, &Student::raiseHand,&teacherDavid, &Teacher::noticeStudent);
    QObject::connect(&s3, &Student::raiseHand,&teacherDavid, &Teacher::noticeStudent);

    teacherDavid.goToTheClass();

    s2.raiseMyHand();

    QTimer myTimer;
    myTimer.start(1000);
    QTimer myTimer2;
    myTimer2.start(500);

    QObject::connect(&myTimer, &QTimer::timeout, &onTime);

    QObject::connect(&myTimer2, &QTimer::timeout, [](){
        std::cout<<"Tack..."<<std::endl;
    });

    return a.exec();
}
