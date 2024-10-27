package seedu.pill.util;

import java.time.LocalDate;

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
     * Initializes the bot, prints the ASCII logo.
     * Prints expired items if any.
     * Prints the list of items to be restocked if there are any.
     * Finally, prints the welcome message.
     *
     * @param items Reference ItemMap to print restock list.
     * @param threshold The minimum number of items before it is deemed to require replenishment.
     */
    public static void printInitMessage(ItemMap items, int threshold){
        System.out.println(ASCII);
        printSpace();
        if (!items.isEmpty()) {
            items.listExpiringItems(LocalDate.now());
            items.listItemsToRestock(threshold);
            printSpace();
        }
        System.out.println("Hello! I'm " + NAME +  "! " + "How can I help you today?");
    }

    /**
     * Exit bot, prints goodbye sequence.
     */
    public static void printExitMessage(){
        System.out.println("Bye. Hope to see you again soon!");
    }
}

