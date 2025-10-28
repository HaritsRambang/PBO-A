class CommandWords
{

    private static final String validCommands[] = {
        "go", "quit", "help", "look"
    };

    public CommandWords()
    {
        // nothing to do at the moment...
    }

    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }

        return false;
    }
}