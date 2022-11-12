#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <QQuickStyle>
#include <QList>
#include "image.h"
#include <QQmlContext>

int main(int argc, char *argv[])
{
#if QT_VERSION < QT_VERSION_CHECK(6, 0, 0)
    QCoreApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
#endif
    QGuiApplication app(argc, argv);

    QQuickStyle::setStyle("Material");


    QList<QObject*> m_images;
    m_images.append(new Image("Penguin", "qrc:/img/peng.jpg"));
    m_images.append( new Image("Kitten", "qrc:/img/kitten.jpg"));
    m_images.append(new Image("Sloth", "qrc:/img/sloth.jpg"));

    QQmlApplicationEngine engine;
    engine.rootContext()->setContextProperty("imgModel", QVariant::fromValue(m_images));

    const QUrl url(QStringLiteral("qrc:/main.qml"));
    QObject::connect(&engine, &QQmlApplicationEngine::objectCreated,
                     &app, [url](QObject *obj, const QUrl &objUrl) {
        if (!obj && url == objUrl)
            QCoreApplication::exit(-1);
    }, Qt::QueuedConnection);
    engine.load(url);

    return app.exec();
}
