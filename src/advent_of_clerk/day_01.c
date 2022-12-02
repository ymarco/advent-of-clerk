/*
** This is an implementation of a state machine that reads goes over the input
** exactly once.
*/

#include <stdio.h>

#define STATE_COLLECT_NUMBER 0
#define STATE_NL 1

typedef struct State {
  int state;
  unsigned int curSum;
  unsigned int curNumberBeingParsed;
  int maxes[3];
} State;

void update_maxes(State *s) {
  if (s->maxes[2] > s->curSum) {
    // throw away curSum
  } else {
    // slot it in
    if (s->maxes[0] < s->curSum) {
      s->maxes[2] = s->maxes[1];
      s->maxes[1] = s->maxes[0];
      s->maxes[0] = s->curSum;
    } else if (s->maxes[1] < s->curSum) {
      s->maxes[2] = s->maxes[1];
      s->maxes[1] = s->curSum;
    } else {
      s->maxes[2] = s->curSum;
    }
  }
}

void advance_state(State *s, char c) {
  switch (s->state) {
  case STATE_COLLECT_NUMBER:
    if ('0' <= c && c <= '9') {
      s->curNumberBeingParsed *= 10;
      s->curNumberBeingParsed += c - '0';
    } else if (c == '\n') {
      s->curSum += s->curNumberBeingParsed;
      s->curNumberBeingParsed = 0;
      s->state = STATE_NL;
    } else {
      printf("Unreachable %d", __LINE__);
    }
    break;
  case STATE_NL:
    if (c == '\n') { // finished with elf
      update_maxes(s);
      s->curSum = 0;
      s->state = STATE_COLLECT_NUMBER;
    } else if ('0' <= c && c <= '9') {
      // start parsing a new number on sum
      s->curNumberBeingParsed *= 10;
      s->curNumberBeingParsed += c - '0';
      s->state = STATE_COLLECT_NUMBER;
    } else {
      printf("Unreachable %d", __LINE__);
    }
    break;
  }
}

void print_state(char c, State *s) {
  printf("%c state %d, curNumberBeingParsed %d, curSum %d, maxes"
         " [%d %d %d]\n",
         c, s->state, s->curNumberBeingParsed, s->curSum, s->maxes[0],
         s->maxes[1], s->maxes[2]);
}

int main() {
  FILE *f;
  if (!(f = fopen("day01-input.txt", "r"))) {
    perror("Can't open data file");
  }
  char buffer[BUFSIZ];
  size_t n;
  State s = {0, 0, 0, {0, 0, 0}};
  while ((n = fread(buffer, sizeof(*buffer), BUFSIZ, f))) {
    for (int i = 0; i < n; i++) {
      advance_state(&s, buffer[i]);
      // print_state(buffer[i], &s)
    }
  }
  if (fclose(f))
    perror("Can't close data file");
  printf("Part 1: %d\n", s.maxes[0]);
  printf("Part 2: %d\n", s.maxes[0] + s.maxes[1] + s.maxes[2]);
}
