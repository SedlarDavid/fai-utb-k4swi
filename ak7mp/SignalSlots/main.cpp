#include <QCoreApplication>
#include "teacher.h"
#include "student.h"
#include <iostream>

void switchOnTheBulb(){
    std::cout << "Light is on!" << std::endl;
}

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    Teacher t1;

    Student s1("Karel");
    Student s2("Pepa");
    Student s3("Venca");

    //Connect signal to some function
    QObject::connect(&t1, &Teacher::teacherIncome, switchOnTheBulb);


    //Connect signal to lambda
    QObject::connect(&t1,&Teacher::teacherIncome, [=](){
        std::cout << "Hello from lambda function!" << std::endl;
    });

    //Connect teacherIncome signal to student's wlcomeTeacher slot
    QObject::connect(&t1,&Teacher::teacherIncome, &s1,&Student::welcomeTeacher);
    QObject::connect(&t1,&Teacher::teacherIncome, &s2,&Student::welcomeTeacher);
    QObject::connect(&t1,&Teacher::teacherIncome, &s3,&Student::welcomeTeacher);


    //Connect riseMyHand signal to teacher's noticeStudent slot
    QObject::connect(&s1, &Student::riseMyHand, &t1, &Teacher::noticeStudent);
    QObject::connect(&s2, &Student::riseMyHand, &t1, &Teacher::noticeStudent);
    QObject::connect(&s3, &Student::riseMyHand, &t1, &Teacher::noticeStudent);

    t1.goToClass();
    s2.riseHand();

    return a.exec();
}
