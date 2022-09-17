#include <QCoreApplication>
#include "student.h"
#include "teacher.h"

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

    return a.exec();
}
