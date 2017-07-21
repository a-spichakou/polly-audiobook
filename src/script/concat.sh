for n in {0..9}; do
    for m in {0..9}; do
      ffmpeg -f concat -safe 0 -i <(for f in ../../output/tts$n$m*.ogg; do echo "file '$PWD/$f'"; done) -c copy ../../output/output$n$m.ogg;
    done
done
