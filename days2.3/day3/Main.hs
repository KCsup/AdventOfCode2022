module Main where

import System.IO
import Control.Monad
import Data.List

main :: IO()
main = do
    input <- readFile "day3input.txt"
    let bags = lines $ input
    let test = getSections (bags!!0)
    print test
    print (priority 'Z')
    print (priority(findDupe test))

    print (sum [priority (findDupe (getSections (bags!!i))) | i <- [0..(length bags - 1)]])
    print (find3 ("E123", "456E", "7E89") )

    let groups = group3 bags
    print groups
    print (sum [priority (find3 (groups!!i)) | i <- [0..(length groups - 1)]])



priority :: Char -> Int
priority c = (happens "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" c)!!0 + 1

happens :: String -> Char -> [Int]
happens string c = flip elemIndices string c

getHappens :: String -> Char -> Char
getHappens string c = do
    if length(happens string c) > 0 then c
    else ' '

getSections :: String -> (String, String)
getSections bag = do
    let len = div (length bag) 2
    (take (len) bag, drop (len) bag)


findDupe :: (String, String) -> Char
findDupe (s1, s2) = 
    (findDupes (s1, s2))!!0

-- Part 2

findDupes :: (String, String) -> String
findDupes (s1, s2) =
    (filter (/=' ') [getHappens s1 (s2!!i) | i <- [0..(length s2 - 1)]])

find3 :: (String, String, String) -> Char
find3 (s1, s2, s3) = findDupe (s1, (findDupes (s2, s3)))

group3 :: [String] -> [(String, String, String)]
group3 strings =
    [(strings!!(i-3), strings!!(i-2), strings!!(i-1)) | i <- [0..(length strings)], (mod i 3) == 0 && i /= 0 ]
    

