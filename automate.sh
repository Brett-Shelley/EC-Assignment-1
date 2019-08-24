#!/bin/bash

func () {
	echo "Running $1"
	java Main "$1.tsp" > "Results/InverOver-$1.csv"
}

name="eil51"
func $name

name="eil101"
func $name

name="eil76"
func $name

name="kroA100"
func $name

name="kroC100"
func $name

name="kroD100"
func $name

name="lin105"
func $name

name="pcb442"
func $name

name="st70"
func $name

name="pr2392"
func $name
