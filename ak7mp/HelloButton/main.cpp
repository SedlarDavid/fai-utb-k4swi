
#include <QApplication>
#include <QPushButton>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QPushButton btn("Hello Btn!");
    btn.show();

    //COnnect button clicked signal to app's exit slot
    QObject::connect(&btn, &QPushButton::clicked,&a, &QApplication::quit);

    return a.exec();
}
