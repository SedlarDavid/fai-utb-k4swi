#ifndef TEACHER_H
#define TEACHER_H

#include <QObject>

class Teacher : public QObject
{
    Q_OBJECT
public:
    explicit Teacher(QObject *parent = nullptr);
    void goToTheClass();

signals:
    void teacherIncome();
};

#endif // TEACHER_H
