#ifndef STUDENT_H
#define STUDENT_H

#include <QObject>
#include <QString>

class Student : public QObject
{
    Q_OBJECT
public:
    explicit Student(QString name, QObject *parent = nullptr);
    void raiseMyHand();

protected:
    QString m_name;

signals:
    void raiseHand(QString name);

public slots:
    void welcomeTeacher();
};

#endif // STUDENT_H
