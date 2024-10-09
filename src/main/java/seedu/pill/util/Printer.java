package seedu.pill.util;

public final class Printer {
    private static final String NAME = "PILL";
    private static final String ASCII = """
              .       . .          ..   .                  . .  . . .      .          .             .:+++:     .
               :&&&&&&&&&&X       ;&&&&&&&&&&&&  .    &&&          ..  .:&&:   . ..     .        .+XXXXXXXXX:  \s
            .  :&&;..  ..&&&&   .      $&&            &&& .  .. .       :&&:                     +X+;xXXXXXXX: \s
               :&&; ..    :&&X   .   . $&&     .   .  &&& ..  .    ..   :&&:     .   .        . ;X+;xXXXXXXXX; \s
            .  :&&;   .   .&&&   . .   $&&          . &&&               :&&:. .          .  . ..Xx;+XXXXXXXXx. \s
               :&&;   .   ;&&+   .  .  $&&  .  .      &&&    . .    .   :&&:             .    .Xx;;XXXXXXXXX.  \s
             ..:&&X+++++$&&&x.         $&&     .   .  &&&.         .    :&&:   .   .     ..  .++::+xXXXXXXX:. ..\s
               :&&&&&&&&&&.            $&&.. .        &&&    .        . :&&:  .  .          .:+::;++++++xX;    \s
               :&&;      .    .        $&&  . .       &&&. .    .     . :&&:              . :++++++++++xx+     \s
            .  :&&;  .   .   ....      $&&  .     .  .&&&   ..          :&&:      ..      . ++++++++++xx+      \s
               :&&;                    $&&.           &&&  ..       ..  :&&: . .           .+++++++++xxx.      \s
               :&&;    ..         :&&&&&&&&&&&&  ...  &&&&&&&&&&&&&..  .:&&&&&&&&&&&&$      ++++++++xxx:       \s
               .XX. .          .  .XXXXXXXXXXXx      .XXXXXXXXXXXXX.    .XXXXXXXXXXXX+   ... .++++xxx;   ..  . \s
                 . . . .        . .            ..  . .    .     .        .           .  .   .   .. ..   .     .\s
            """;


    /**
     * Prints a horizontal line.
     */
    public static void printSpace(){
        System.out.println("\n");
    }

    /**
     * Initializes the bot, prints the logo and a welcome message.
     */
    public static void printInitMessage(){
        System.out.println(ASCII);
        System.out.println("Hello! I'm " + NAME +  "! " + "How can I help you today?");
    }

    /**
     * Exit bot, prints goodbye sequence.
     */
    public static void printExitMessage(){
        System.out.println("Bye. Hope to see you again soon!");
    }
}

