#include "teacher.h"

Teacher::Teacher(QObject *parent)
    : QObject{parent}
{

}

void Teacher::goToTheClass()
{
    emit teacherIncome();
}


