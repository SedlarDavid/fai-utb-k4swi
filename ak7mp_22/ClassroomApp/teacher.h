#ifndef TEACHER_H
#define TEACHER_H

#include <QString>
#include <QObject>

class Teacher : public QObject
{
    Q_OBJECT
public:
    explicit Teacher(QObject *parent = nullptr);
    void goToTheClass();

signals:
    void teacherIncome();

public slots:
    void noticeStudent(QString name);
};

#endif // TEACHER_H
