#ifndef STUDENT_H
#define STUDENT_H

#include <QObject>

class Student : public QObject
{
    Q_OBJECT
public:
    explicit Student(QString name, QObject *parent = nullptr);

    void riseHand();

protected:
    QString m_name;

signals:
    void riseMyHand(QString myName);

public slots:

    void  welcomeTeacher();
};

#endif // TEACHER_H
