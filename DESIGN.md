---
name: Student Exam 44844
description: Native form and student card reproduced from the supplied exam.
colors:
  page: "#FFFFFF"
  card-page: "#FCF7FF"
  form: "#E1F7FA"
  surface: "#FFFFFF"
  title: "#087D8C"
  primary: "#007F9F"
  text: "#20252A"
  secondary: "#555D65"
  avatar: "#5C9FCD"
  status: "#467C0B"
  error: "#B3261E"
  night-page: "#131B20"
  night-form: "#20373E"
  night-surface: "#202A31"
  night-text: "#F1F5F7"
  night-secondary: "#C1CDD4"
typography:
  title:
    fontFamily: sans-serif-medium
    fontSize: 26sp
  body:
    fontFamily: sans-serif
    fontSize: 16sp
  label:
    fontFamily: sans-serif
    fontSize: 14sp
spacing:
  screen: 16dp
  field: 12dp
  card: 16dp
rounded:
  card: 8dp
  button: 24dp
---

## Overview

Match the supplied two screens: teal centered title, pale cyan form, bottom cyan
submit action; second screen has a single white student card on a pale lavender
page, blue avatar and pencil, name, ID, class and green studying status.

## Colors

Use paired light/night resource values for every surface, input, label and error.
Use readable secondary text rather than the screenshot's very faint gray.
On API35+ transparent bars use dark icons in light mode and white icons at night.

## Typography

Use sp, wrapping text and Vietnamese resource strings. Names and long form input
must fit without forcing the page wider than the screen.

## Layout

Form: title, a weighted ScrollView with centered form, then a bottom submit button
and final TextView for validation messages. Cap form width at480dp on wide screens.
Card: horizontal avatar, weighted text column and48dp edit action. Cap width600dp.
Apply system insets and adjustResize; keep primary actions visible with keyboard.

## Elevation & Depth

Cards have2dp native elevation. No custom animation or decorative screen chrome.

## Shapes

Use8dp rounded cards and a24dp native ripple submit shape; vector avatar/pencil.

## Components

Persistent labels and input hints identify each field; controls have48dp touch
targets. A separate inset wrapper preserves content padding. Errors
appear at inputs and in a live-region TextView at the end. RadioGroup selects
one gender. The pencil returns to the form without discarding entered values.
No loading states or remote dependencies are needed.
