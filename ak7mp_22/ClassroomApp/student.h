#ifndef STUDENT_H
#define STUDENT_H

#include <QObject>
#include <QString>

class Student : public QObject
{
    Q_OBJECT
public:
    explicit Student(QString name, QObject *parent = nullptr);

protected:
    QString m_name;

signals:
    void raiseHand();

public slots:
    void welcomeTeacher();
};

#endif // STUDENT_H
