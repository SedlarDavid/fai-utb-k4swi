import QtQuick 2.15
import QtQuick.Window 2.15

Window {
    width: 640
    height: 480
    visible: true
    title: qsTr("Hello World")

    Timer {
    id:myTimer
    interval:1000
    running:true
    repeat:true
    onTriggered: {
         myRect.x = Math.random() * (myRect.parent.width - myRect.width)
        myRect.y = Math.random() * (myRect.parent.height - myRect.height)
}
    }

    Text{
    anchors.left: playGround.left
    anchors.bottom: playGround.top
    text: qsTr("Score: ") + myRect.clickCount

    }

    Rectangle {
    id: playGround
    anchors.fill: parent
    anchors.margins: 10
    anchors.topMargin: 20
    color:"lightgray"
    clip:true

     MouseArea{
     anchors.fill: parent
     onClicked: {
     myRect.clickCount--
     myTimer.interval += 50
         myRect.attentionColor = "red"
         attAnim.start()
     }}

    Rectangle {
    id: myRect
    property int clickCount:0
    property color attentionColor: "green"
    x:100
    y:100
    width: (parent.width>800) ? 100 : (parent.width/8)
    height:(parent.width>600) ? 100 : (parent.height/6)
    color:"lightblue"
    border.color: "black"
    border.width: 2
    radius: 10

    focus: true
    Keys.onUpPressed: y -= 10
    Keys.onDownPressed: y += 10
    Keys.onLeftPressed: x -= 10
    Keys.onRightPressed: x += 10

    Behavior on x {
        NumberAnimation {
        duration: myTimer.interval
        easing.type: Easing.OutElastic
        }
    }
    Behavior on y {
        NumberAnimation {
        duration: myTimer.interval
        easing.type: Easing.OutElastic
        }
    }
SequentialAnimation {
id: attAnim

ColorAnimation {
    from: "lightblue"
    to: myRect.attentionColor
    duration: myTimer.interval/2
    target: myRect
    property: "color"
}
ColorAnimation {
    from: myRect.attentionColor
    to: "lightblue"
    duration: myTimer.interval/2
    target: myRect
    property: "color"
}

}

    Text{
        anchors.centerIn: parent
    text: qsTr("Click me...")
    }

    MouseArea{
    anchors.fill: parent
    onClicked: {
   myRect.x = Math.random() * (myRect.parent.width - myRect.width)
        myRect.y = Math.random() * (myRect.parent.height - myRect.height)
myRect.clickCount++
        myTimer.interval -= 50
        myRect.attentionColor = "green"
        attAnim.start()
        }
    }

    }

    }



}
