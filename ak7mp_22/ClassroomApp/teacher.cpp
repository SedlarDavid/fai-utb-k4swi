#include <iostream>
#include "teacher.h"

Teacher::Teacher(QObject *parent)
    : QObject{parent}
{

}

void Teacher::goToTheClass()
{
    emit teacherIncome();
}

void Teacher::noticeStudent(QString name)
{
 std::cout << "Noticed student with name: " << name.toStdString() << std::endl;
}


