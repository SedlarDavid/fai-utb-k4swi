#ifndef IMAGE_H
#define IMAGE_H

#include <QObject>

class Image : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString name READ name WRITE setName NOTIFY nameChanged);
    Q_PROPERTY(QString url READ url WRITE setUrl NOTIFY urlChanged);
public:
    explicit Image(QString newName, QString newUrl, QObject *parent = nullptr);

    const QString &name() const;
    const QString &url() const;

public slots:
    void setUrl(const QString &newUrl);
    void setName(const QString &newName);

signals:
    void urlChanged(const QString& url);
    void nameChanged(const QString& name);

private:
    QString m_name;
    QString m_url;

};

#endif // IMAGE_H
