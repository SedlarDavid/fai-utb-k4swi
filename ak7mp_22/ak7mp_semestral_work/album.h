#ifndef ALBUM_H
#define ALBUM_H
#include <QString>


class Album
{
public:
    Album(const int &id, const QString &name,const QString &performerName,const QString &genre,const QString &img,const int &releaseYear )
        : m_id(id), m_name(name), m_performer_name(performerName),m_genre(genre), m_image(img), m_release_year(releaseYear)
    {
    }

    int id() const { return m_id; }

    QString name() const { return m_name; }
    QString performerName() const { return m_performer_name; }
    QString genre() const { return m_genre; }
    QString image() const { return m_image; }
    int releaseYear() const { return m_release_year; }

    Album();
private:
    int m_id;
    QString m_name;
    QString m_performer_name;
    QString m_genre;
    QString m_image;
    int m_release_year;
};

#endif // ALBUM_H
