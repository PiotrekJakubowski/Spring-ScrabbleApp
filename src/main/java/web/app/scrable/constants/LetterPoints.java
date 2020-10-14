package web.app.scrable.constants;

public enum LetterPoints {
    a('a',1),
    ą('ą',5),
    b('b',3),
    c('c',2),
    ć('ć',6),
    d('d',2),
    e('e',1),
    ę('ę',5),
    f('f',5),
    g('g',3),
    h('h',3),
    i('i',1),
    j('j',3),
    k('k',2),
    l('l',2),
    ł('ł',3),
    m('m',2),
    n('n',1),
    ń('ń',7),
    o('o',1),
    ó('ó',5),
    p('p',2),
    r('r',1),
    s('s',1),
    ś('ś',5),
    t('t',2),
    u('u',3),
    w('w',1),
    y('y',2),
    z('z',1),
    ź('ź',9),
    ż('ż',5);

    private final Character letter;
    private final int value;

    private LetterPoints(final Character letter, final int value) {
        this.letter = letter;
        this.value = value;
    }

    public Character getLetter() {
        return letter;
    }

    public int getValue() {
        return value;
    }

    public static int getValueForLetter(final Character letter) {
        for(LetterPoints lp: values()) {
            if(lp.getLetter().compareTo(Character.toLowerCase(letter)) == 0) {
                return lp.getValue();
            }
        }
        throw new IllegalArgumentException("Invalid letter: " + letter);
    }
}
