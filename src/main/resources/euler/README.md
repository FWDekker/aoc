Euler Project problems can usually be described with some kind of input or parameter.
That's what the files in this directory are for.

For problem number `X`, add a file `ProblemX.txt` to this directory.
This file will be fed to the `ProblemX` class when you run it.
See those respective classes for details on the expected input format.

Each problem `X` also has an associated class `ProblemXTest`.
This class also reads `ProblemX.txt`, and additionally looks for `ProblemXSolution.txt` to compare the implementation's solution against the actual solution.
Note that these solution files should not be committed and are therefore in the `.gitignore`.

Some problems also provide a sample input and output in the problem description.
These can be described by providing `ProblemXSampleY.txt` and `ProblemXSampleYSolution.txt` for any sample with (custom) number `Y`.
Solutions for samples can be committed because they are taken from the problem description, which is public.
