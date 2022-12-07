module Main where

import System.IO  
import Control.Monad

main :: IO ()
main = do
    input <- readFile "day2input.txt"
    let rounds = lines $ input
    -- let test = rounds!!3
    -- let score = totalScore (take 1 test) (drop 2 test)
    let final = sum [ totalScore (take 1 (rounds!!i)) (drop 2 (rounds!!i)) | i <- [0..(length rounds - 1)]]
    print final

    let final2 = sum [part2 (take 1 (rounds!!i)) (drop 2 (rounds!!i)) | i <- [0..(length rounds - 1)]]
    print final2


shapeScore :: String -> Int
shapeScore shape =
  if shape == "A" || shape == "X" then 1
  else if shape == "B" || shape == "Y" then 2
  else if shape == "C" || shape == "Z" then 3
  else 0


{-
 - A, X: Rock
 - B, Y: Paper
 - C, Z: Scissors
 - (For part 1)
 -}
outcomeScore :: String -> String -> Int
outcomeScore shape choice =
  if shape == "A" then
    (if choice == "X" then 3
    else if choice == "Y" then 6
    else if choice == "Z" then 0
    else 0)
  else if shape == "B" then
    (if choice == "X" then 0
    else if choice == "Y" then 3
    else if choice == "Z" then 6
    else 0)
  else if shape == "C" then do
    (if choice == "X" then 6
    else if choice == "Y" then 0
    else if choice == "Z" then 3
    else 0)
  else 0


{-
 - X: Loss
 - Y: Tie
 - Z: Win
 -}

part2 :: String -> String -> Int
part2 shape choice =
  if choice == "X" then shapeScore (getLoss shape)
  else if choice == "Y" then shapeScore (getTie shape) + 3
  else if choice == "Z" then shapeScore (getWin shape) + 6
  else 0

getLoss :: String -> String
getLoss shape =
  if shape == "A" then "Z"
  else if shape == "B" then "X"
  else if shape == "C" then "Y"
  else ""

getWin :: String -> String
getWin shape =
  if shape == "A" then "Y"
  else if shape == "B" then "Z"
  else if shape == "C" then "X"
  else ""

getTie :: String -> String
getTie shape =
  if shape == "A" then "X"
  else if shape == "B" then "Y"
  else if shape == "C" then "Z"
  else ""

totalScore :: String -> String -> Int
totalScore shape choice = shapeScore choice + outcomeScore (shape) (choice)

