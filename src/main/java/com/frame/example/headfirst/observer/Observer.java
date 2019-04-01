package com.frame.example.headfirst.observer;//package com.headfirst.observer;
//
//import java.io.Console;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.List;
//
///// <summary>
///// 抽象主题类
///// </summary>
//abstract class Subject
//{
//    private List<Observer> observers = new ArrayList<Observer>();
//
//    /// <summary>
//    /// 增加观察者
//    /// </summary>
//    /// <param name="observer"></param>
//    public void Attach(Observer observer)
//    {
//        observers.add(observer);
//    }
//
//    /// <summary>
//    /// 移除观察者
//    /// </summary>
//    /// <param name="observer"></param>
//    public void Detach(Observer observer)
//    {
//        observers.remove(observer);
//    }
//
//    /// <summary>
//    /// 向观察者（们）发出通知
//    /// </summary>
//    public void Notify()
//    {
//        foreach (Observer o in observers)
//        {
//            o.Update();
//        }
//    }
//}
//
///// <summary>
///// 抽象观察者类，为所有具体观察者定义一个接口，在得到通知时更新自己
///// </summary>
//public abstract class Observer
//{
//    public abstract void Update();
//}
//
///// <summary>
///// 具体观察者或具体通知者，将有关状态存入具体观察者对象；在具体主题的内部状态改变时，给所有登记过的观察者发出通知。具体主题角色通常用一个具体子类实现。
///// </summary>
// class ConcreteSubject : Subject
//{
//    private string subjectState;
//
//    /// <summary>
//    /// 具体观察者的状态
//    /// </summary>
//    public string SubjectState
//    {
//        get { return subjectState; }
//        set { subjectState = value; }
//    }
//}
//
///// <summary>
///// 具体观察者，实现抽象观察者角色所要求的更新接口，已是本身状态与主题状态相协调
///// </summary>
// class ConcreteObserver extends Observer {
//    private String observerState;
//    private String name;
//    private ConcreteSubject subject;
//
//    /// <summary>
//    /// 具体观察者用一个具体主题来实现
//    /// </summary>
//    public ConcreteSubject Subject
//    {
//        get { return subject; }
//        set { subject = value; }
//    }
//
//    public ConcreteObserver(ConcreteSubject subject, string name)
//    {
//        this.subject = subject;
//        this.name = name;
//    }
//
//    /// <summary>
//    /// 实现抽象观察者中的更新操作
//    /// </summary>
//    public override void Update()
//    {
//        observerState = subject.SubjectState;
//        System.out.println(MessageFormat.format("The observer's state of {0} is {1}", name, observerState));
//    }
//}
