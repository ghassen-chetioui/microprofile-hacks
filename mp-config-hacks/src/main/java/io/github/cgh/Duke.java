package io.github.cgh;

public class Duke {

    private final String name;
    private final int age;

    public Duke(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Duke{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
