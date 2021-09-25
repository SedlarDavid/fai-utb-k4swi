#ifndef TEACHER_H
#define TEACHER_H

#include <QObject>

class Teacher : public QObject
{
    Q_OBJECT
public:
    explicit Teacher(QObject *parent = nullptr);

    void goToClass();

public slots:
    void noticeStudent(QString studentName);

signals:
    void teacherIncome();
};

#endif // TEACHER_H
