AoC problems can usually be described with some kind of input or parameter.
That's what the files in these directories are for.
Each year of AoC has its own directory.

For day number `X`, add a file `DayX.txt` to this directory.
This file will be fed to the `DayX` class when you run it.
See those respective classes for details on the expected input format.

Each day `X` also has an associated class `DayXTest`.
This class also reads `DayX.txt`, and compares it with the correct output which is given in `DayXTest`.

Some problems also provide a sample input and output in the problem description.
These can be described by providing `DayXSampleY.txt` for any sample with (custom) number `Y`.
