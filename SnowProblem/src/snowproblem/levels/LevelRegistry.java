package snowproblem.levels;

import java.util.ArrayList;
import java.util.List;

public class LevelRegistry {

    // Stores every level in order so the game can load them from the selector.
    private static final List<Level> levels = new ArrayList<>();

    // Adds all the levels to the list when the class is first loaded.
    static {
    registerLevel(Level.fromMap(
    1, "Starter 1", 1, "blue",
    ".S...",
    ".....",
    ".....",
    "B...L"
));

registerLevel(Level.fromMap(
    2, "Starter 2", 1, "yellow",
    ".....",
    "L..Y.",
    ".T..S",
    "....."
));

registerLevel(Level.fromMap(
    3, "Starter 3", 1, "red",
    "T..L.",
    "...S.",
    "....R",
    ".T..."
));

registerLevel(Level.fromMap(
    4, "Starter 4", 1, "yellow",
    "T...S",
    ".....",
    ".....",
    "Y...L"
));

registerLevel(Level.fromMap(
    5, "Starter 5", 1, "blue",
    "T.T.T",
    "..B..",
    ".....",
    "S...L"
));

registerLevel(Level.fromMap(
    6, "Starter 6", 1, "blue",
    "..T..",
    "BS.L.",
    ".....",
    "..T.."
));

registerLevel(Level.fromMap(
    7, "Starter 7", 1, "red",
    ".....",
    ".TT..",
    "....R",
    "S.L.."
));

registerLevel(Level.fromMap(
    8, "Starter 8", 1, "blue",
    ".T.SL",
    ".....",
    "....T",
    "..B.."
));

registerLevel(Level.fromMap(
    9, "Starter 9", 1, "red",
    "T.S..",
    "...R.",
    ".....",
    ".L..T"
));

registerLevel(Level.fromMap(
    10, "Starter 10", 1, "red",
    "T.T..",
    ".T...",
    "...R.",
    ".S..L"
));

registerLevel(Level.fromMap(
    11, "Starter 11", 1, "yellow",
    "TT.Y.",
    ".....",
    "S...T",
    "L...."
));

registerLevel(Level.fromMap(
    12, "Starter 12", 1, "blue",
    "TL..T",
    ".....",
    ".B.S.",
    "....."
));

registerLevel(Level.fromMap(
    13, "Starter 13", 1, "red",
    "T.S..",
    "....R",
    ".T...",
    "....L"
));

registerLevel(Level.fromMap(
    14, "Starter 14", 1, "red",
    ".T...",
    "...T.",
    "S....",
    "..R.L"
));

registerLevel(Level.fromMap(
    15, "Starter 15", 1, "blue",
    "TT..S",
    "....B",
    "L....",
    "..T.."
));

registerLevel(Level.fromMap(
    16, "Starter 16", 1, "blue",
    "TT...",
    "....B",
    "S....",
    "LT..."
));

registerLevel(Level.fromMap(
    17, "Junior 17", 1, "red",
    ".T...",
    "....T",
    "T..S.",
    "...RL"
));

registerLevel(Level.fromMap(
    18, "Junior 18", 1, "blue",
    "..TSL",
    "T....",
    "...T.",
    ".B..."
));

registerLevel(Level.fromMap(
    19, "Junior 19", 1, "yellow",
    "..T.S",
    "T...T",
    ".Y...",
    "...L."
));

registerLevel(Level.fromMap(
    20, "Junior 20", 1, "yellow",
    ".T...",
    "...Y.",
    "L....",            
    "..T.S"
));

registerLevel(Level.fromMap(
    21, "Junior 21", 1, "blue",
    "...B.",
    "T....",
    "....T",
    "S.L.."
));

registerLevel(Level.fromMap(
    22, "Junior 22", 1, "blue",
    ".T.S.",
    "T....",
    "B....",
    "..T.L"
));

registerLevel(Level.fromMap(
    23, "Junior 23", 1, "blue",
    "..T..",
    "T...B",
    ".T...",
    "L...S"
));

registerLevel(Level.fromMap(
    24, "Junior 24", 1, "blue",
    "..B..",
    "T...T",
    ".T...",
    ".S..L"
));

registerLevel(Level.fromMap(
    25, "Junior 25", 1, "blue",
    "T.T.S",
    ".....",
    "...B.",
    "LT..."
));

registerLevel(Level.fromMap(
    26, "Junior 26", 1, "red",
    "T.L..",
    "....T",
    "T....",
    "S..R."
));

registerLevel(Level.fromMap(
    27, "Junior 27", 1, "red",
    ".T..S",
    "....T",
    "R....",
    "..TL."
));

registerLevel(Level.fromMap(
    28, "Junior 28", 1, "blue",
    "T....",
    "....T",
    "S....",
    "L..B."
));

registerLevel(Level.fromMap(
    29, "Master 29", 2, "blue",
    ".....",
    ".Y.B.",
    "LS.SL",
    "....."
));

registerLevel(Level.fromMap(
    30, "Master 30", 2, "red",
    "R.T.S",
    "L....",
    "...S.",
    "YLT.."
));

registerLevel(Level.fromMap(
    31, "Master 31", 2, "red",
    "L.B.L",
    "T.R..",
    "....S",
    "..S.."
));

registerLevel(Level.fromMap(
    32, "Master 32", 2, "red",
    "..R.L",
    ".T.Y.",
    "..T..",
    "S.L.S"
));     

registerLevel(Level.fromMap(
    33, "Master 33", 2, "red",
    "L...S",
    "R.Y..",
    ".....",
    "..S.L"
));

registerLevel(Level.fromMap(
    34, "Master 34", 2, "blue",
    "L.BSL",
    "T....",
    "T..Y.",
    "...S."
));

registerLevel(Level.fromMap(
    35, "Master 35", 2, "yellow",
    "T.TLY",
    ".....",
    "....S",
    "LS.R."
));

registerLevel(Level.fromMap(
    36, "Master 36", 2, "red",
    "L.Y.S",
    "RT...",
    "..T..",
    "L...S"
));

registerLevel(Level.fromMap(
    37, "Master 37", 2, "yellow",
    "Y.L.S",
    ".T.B.",
    "...T.",
    "L.S.."
));

registerLevel(Level.fromMap(
    38, "Master 38", 2, "yellow",
    "LL.S.",
    "TY.BT",
    ".....",
    "..S.."
));

registerLevel(Level.fromMap(
    39, "Master 39", 2, "blue",
    "B..L.",
    "S.T..",
    "....S",
    "L.R.."
));

registerLevel(Level.fromMap(
    40, "Master 40", 2, "blue",
    "TB.LS",
    "R.S..",
    ".....",
    "L..T."
));

registerLevel(Level.fromMap(
    41, "Master 41", 2, "blue",
    "L...S",
    "..B..",
    "S.Y..",
    "L...."
));

registerLevel(Level.fromMap(
    42, "Master 42", 2, "red",
    "L...S",
    ".R...",
    "L.Y.S",
    "....."
));

registerLevel(Level.fromMap(
    43, "Master 43", 2, "blue",
    "SBT.S",
    ".....",
    ".....",
    "L.R.L"
));

registerLevel(Level.fromMap(
    44, "Master 44", 2, "red",
    "....L",
    ".TRT.",
    "..Y..",
    "S..LS"
));

registerLevel(Level.fromMap(
    45, "Master 45", 2, "blue",
    ".T...",
    "S.S.Y",
    ".B.T.",
    "L...L"
));

registerLevel(Level.fromMap(
    46, "Master 46", 2, "blue",
    "LSTLB",
    ".....",
    "TY..S",
    "....."
));

registerLevel(Level.fromMap(
    47, "Master 47", 2, "blue",
    "L.R.S",
    ".T.TS",
    ".....",
    "B...L"
));

registerLevel(Level.fromMap(
    48, "Master 48", 2, "yellow",
    ".T..L",
    ".S..T",
    ".....",
    ".YSRL"
));

registerLevel(Level.fromMap(
    49, "Expert 49", 3, "blue",
    "SSSL.",
    "T.YT.",
    ".LR..",
    "..B.L"
));

registerLevel(Level.fromMap(
    50, "Expert 50", 3, "blue",
    "YLS.S",
    "..T.B",
    "L.T..",
    ".RS.L"
));

registerLevel(Level.fromMap(
    51, "Expert 51", 3, "blue",
    "LL.R.",
    "T...B",
    "L.T..",
    "SSSY."
));

registerLevel(Level.fromMap(
    52, "Expert 52", 3, "blue",
    "TL..L",
    "...SB",
    "S..RL",
    ".SY.."
));

registerLevel(Level.fromMap(
    53, "Expert 53", 2, "blue",
    "L.T..",
    ".Y.B.",
    "T....",
    "SS..L"
));

registerLevel(Level.fromMap(
    54, "Expert 54", 2, "blue",
    "..L..",
    ".T..Y",
    ".T..B",
    "SS..L"
));

registerLevel(Level.fromMap(
    55, "Expert 55", 2, "blue",
    "LTRBL",
    ".....",
    ".....",
    "S..TS"
));

registerLevel(Level.fromMap(
    56, "Expert 56", 2, "yellow",
    ".T.TS",
    ".....",
    "S.Y.L",
    ".R.L."
));

registerLevel(Level.fromMap(
    57, "Expert 57", 2, "red",
    "..T..",
    "T.SR.",
    ".S..Y",
    "L...L"
));

registerLevel(Level.fromMap(
    58, "Expert 58", 2, "yellow",
    "TS...",
    "....Y",
    "T.L..",
    "L..RS"
));

registerLevel(Level.fromMap(
    59, "Expert 59", 2, "blue",
    "L..L.",
    ".T..B",
    "R..T.",
    ".S..S"
));

registerLevel(Level.fromMap(
    60, "Expert 60", 2, "blue",
    "LS..B",
    ".TT..",
    "R....",
    ".S..L"
));

registerLevel(Level.fromMap(
    61, "Expert 61", 3, "blue",
    "Y.R.S",
    ".B.LT",
    "LSS..",
    "...L."
));

registerLevel(Level.fromMap(
    62, "Expert 62", 3, "blue",
    "SSSY.",
    ".TLLB",
    ".T...",
    "...RL"
));

registerLevel(Level.fromMap(
    63, "Expert 63", 3, "blue",
    "..L.S",
    "..T.S",
    "R.Y.B",
    "S.L.L"
));

registerLevel(Level.fromMap(
    64, "Expert 64", 3, "blue",
    "S.B..",
    "TR.YL",
    "...ST",
    "LL..S"
));

registerLevel(Level.fromMap(
    65, "Expert 65", 2, "blue",
    ".Y.B.",
    "TSL..",
    "..S.T",
    "..L.."
));

registerLevel(Level.fromMap(
    66, "Expert 66", 2, "yellow",
    ".Y..L",
    ".....",
    "S....",
    "..RSL"
));

registerLevel(Level.fromMap(
    67, "Expert 67", 2, "blue",
    ".L..L",
    "T...S",
    "R....",
    "..BS."
));

registerLevel(Level.fromMap(
    68, "Expert 68", 2, "yellow",
    "T.YS.",
    "ST...",
    "....L",
    "L.R.."
));

registerLevel(Level.fromMap(
    69, "Expert 69", 3, "blue",
    "L.RBL",
    "ST...",
    "...TY",
    "L..SS"
));

registerLevel(Level.fromMap(
    70, "Expert 70", 3, "blue",
    ".TSS.",
    ".T.SL",
    "RL...",
    "Y.LB."
));

registerLevel(Level.fromMap(
    71, "Expert 71", 3, "blue",
    "L.T.S",
    "Y.T.S",
    "BLS..",
    "L...R"
));

registerLevel(Level.fromMap(
    72, "Expert 72", 3, "blue",
    "LL.LS",
    "TR...",
    "BY...",
    "..S.S"
));

registerLevel(Level.fromMap(
    73, "Expert 73", 2, "blue",
    ".Y..S",
    ".....",
    "L...S",
    "...BL"
));

registerLevel(Level.fromMap(
    74, "Expert 74", 2, "blue",
    "TLL..",
    ".SS.R",
    "B....",
    "...T."
));

registerLevel(Level.fromMap(
    75, "Expert 75", 2, "blue",
    "ST...",
    "....R",
    "S...B",
    "LT..L"
));

registerLevel(Level.fromMap(
    76, "Expert 76", 2, "blue",
    "L..BS",
    "T.L..",
    "....R",
    ".S..."
));

registerLevel(Level.fromMap(
    77, "Expert 77", 3, "blue",
    "TS.L.",
    "R...S",
    "..TSL",
    ".B.YL"
));

registerLevel(Level.fromMap(
    78, "Expert 78", 3, "blue",
    ".BYRL",
    ".....",
    "S...L",
    "SSL.."
));

registerLevel(Level.fromMap(
    79, "Expert 79", 3, "blue",
    "T.L.T",
    "RBS..",
    "YLS..",
    "..S.L"
));

registerLevel(Level.fromMap(
    80, "Expert 80", 3, "blue",
    "ST.B.",
    ".LLSL",
    "T..S.",
    "...YR"
));

    }

    // Adds one level to the level list.
    private static void registerLevel(Level level) {
        levels.add(level);
    }

    // Returns all levels so the level selector can display them.
    public static List<Level> getAllLevels() {
        return levels;
    }

    // Finds a level by its level number.
    public static Level getLevel(int number) {
        for (Level l : levels) {
            if (l.getNumber() == number) {
                return l;
            }
        }

        return null;
    }

    // Returns the total number of levels in the game.
    public static int count() {
        return levels.size();
    }
}