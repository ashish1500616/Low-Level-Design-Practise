# Chapter 4: A Real-World Journey - Building a Multimedia Player

## The Challenge: A Multimedia Player System

Imagine you're building the next great multimedia application. Users can play videos, music, and even live streams. This is a perfect opportunity to see LSP in action in a real-world scenario.

## First Iteration: The Naive Approach (What Not To Do)

```java
// ❌ Problematic Implementation
class MediaPlayer {
    private String mediaSource;
    
    void play() { /* Play logic */ }
    void pause() { /* Pause logic */ }
    void stop() { /* Stop logic */ }
    void seekTo(long position) { /* Seek logic */ }
    double getPlaybackSpeed() { return 1.0; }
}

class VideoPlayer extends MediaPlayer {
    void showSubtitles() { /* Subtitle logic */ }
}

class LiveStreamPlayer extends MediaPlayer {
    @Override
    void seekTo(long position) {
        throw new UnsupportedOperationException("Cannot seek in live stream!");
    }
}
```

## The Problems Emerge

1. Live streams can't seek backward
2. Some media types don't support variable playback speed
3. Audio-only files don't need video-related features
4. Different media types have different buffering requirements

## The LSP-Compliant Solution

```java
// 1. Core Interfaces
interface Playable {
    void play();
    void pause();
    void stop();
    PlaybackState getState();
}

interface Seekable {
    boolean canSeek();
    void seekTo(long position);
}

interface SpeedControllable {
    boolean supportsVariableSpeed();
    void setPlaybackSpeed(double speed);
    double getPlaybackSpeed();
}

// 2. States and Events
enum PlaybackState {
    PLAYING, PAUSED, STOPPED, BUFFERING, ERROR
}

interface PlaybackEventListener {
    void onStateChanged(PlaybackState newState);
    void onProgress(long position, long duration);
    void onError(String message);
}

// 3. Media-Specific Components
class AudioPlayer implements Playable, Seekable, SpeedControllable {
    private PlaybackState state = PlaybackState.STOPPED;
    private double playbackSpeed = 1.0;
    private final List<PlaybackEventListener> listeners = new ArrayList<>();

    @Override
    public void play() {
        setState(PlaybackState.PLAYING);
    }

    @Override
    public void pause() {
        setState(PlaybackState.PAUSED);
    }

    @Override
    public void stop() {
        setState(PlaybackState.STOPPED);
    }

    @Override
    public PlaybackState getState() {
        return state;
    }

    @Override
    public boolean canSeek() {
        return true;
    }

    @Override
    public void seekTo(long position) {
        // Implementation
    }

    @Override
    public boolean supportsVariableSpeed() {
        return true;
    }

    @Override
    public void setPlaybackSpeed(double speed) {
        this.playbackSpeed = speed;
    }

    @Override
    public double getPlaybackSpeed() {
        return playbackSpeed;
    }

    private void setState(PlaybackState newState) {
        state = newState;
        notifyListeners();
    }

    private void notifyListeners() {
        for (PlaybackEventListener listener : listeners) {
            listener.onStateChanged(state);
        }
    }
}

// 4. Video Player with Additional Features
class VideoPlayer extends AudioPlayer {
    private boolean subtitlesEnabled = false;
    private final VideoRenderer renderer;

    public VideoPlayer(VideoRenderer renderer) {
        this.renderer = renderer;
    }

    public void toggleSubtitles() {
        subtitlesEnabled = !subtitlesEnabled;
        renderer.showSubtitles(subtitlesEnabled);
    }
}

// 5. Live Stream Player
class LiveStreamPlayer implements Playable {
    private PlaybackState state = PlaybackState.STOPPED;
    private final BufferingStrategy bufferingStrategy;

    public LiveStreamPlayer(BufferingStrategy bufferingStrategy) {
        this.bufferingStrategy = bufferingStrategy;
    }

    @Override
    public void play() {
        bufferingStrategy.startBuffering();
        setState(PlaybackState.BUFFERING);
        // Once buffered enough, set to PLAYING
    }

    @Override
    public void pause() {
        // Maybe not allow pause for live content
        throw new UnsupportedOperationException("Live streams cannot be paused");
    }

    @Override
    public void stop() {
        setState(PlaybackState.STOPPED);
        bufferingStrategy.clear();
    }

    @Override
    public PlaybackState getState() {
        return state;
    }

    private void setState(PlaybackState newState) {
        state = newState;
    }
}

// 6. Usage Example
class MediaPlayerApp {
    public void demonstratePlayback() {
        // Playing a regular audio file
        Playable audioPlayer = new AudioPlayer();
        playContent(audioPlayer);

        // Playing a video
        VideoPlayer videoPlayer = new VideoPlayer(new DefaultVideoRenderer());
        playContent(videoPlayer);
        
        if (videoPlayer instanceof SpeedControllable) {
            SpeedControllable speedControl = (SpeedControllable) videoPlayer;
            if (speedControl.supportsVariableSpeed()) {
                speedControl.setPlaybackSpeed(1.5);
            }
        }

        // Playing a live stream
        Playable liveStream = new LiveStreamPlayer(new AdaptiveBufferingStrategy());
        playContent(liveStream);
    }

    private void playContent(Playable content) {
        content.play();
        
        // We can safely call these methods on any Playable
        if (content.getState() == PlaybackState.PLAYING) {
            System.out.println("Content is playing!");
        }
    }
}

// 7. Support Classes
interface VideoRenderer {
    void showSubtitles(boolean show);
    void render(byte[] frameData);
}

interface BufferingStrategy {
    void startBuffering();
    void clear();
    boolean hasEnoughData();
}

class DefaultVideoRenderer implements VideoRenderer {
    @Override
    public void showSubtitles(boolean show) {
        // Implementation
    }

    @Override
    public void render(byte[] frameData) {
        // Implementation
    }
}

class AdaptiveBufferingStrategy implements BufferingStrategy {
    @Override
    public void startBuffering() {
        // Implementation
    }

    @Override
    public void clear() {
        // Implementation
    }

    @Override
    public boolean hasEnoughData() {
        // Implementation
        return true;
    }
}
```

## Key Design Decisions That Maintain LSP

1. **Interface Segregation**:
   - Separated `Playable`, `Seekable`, and `SpeedControllable`
   - Each media type implements only what it can support

2. **Clear Contracts**:
   - Each interface has a specific responsibility
   - No unexpected exceptions
   - Clear state management

3. **Composition Over Inheritance**:
   - Video player uses composition for rendering
   - Live stream player uses composition for buffering

4. **Explicit Capability Checking**:
   - Features are discovered through interface checks
   - No assumptions about capabilities

## Testing the Design

```java
class MediaPlayerTest {
    @Test
    void testPlaybackStateTransitions() {
        List<Playable> players = Arrays.asList(
            new AudioPlayer(),
            new VideoPlayer(new DefaultVideoRenderer()),
            new LiveStreamPlayer(new AdaptiveBufferingStrategy())
        );

        for (Playable player : players) {
            // These should work for ALL players
            player.play();
            assert player.getState() == PlaybackState.PLAYING;
            player.stop();
            assert player.getState() == PlaybackState.STOPPED;
        }
    }
}
```

## Lessons from Real-World Implementation

1. **Interface Discovery**: Use interface checks instead of type checks
2. **Feature Availability**: Make capabilities explicit through interfaces
3. **Error Handling**: No unexpected exceptions, clear state transitions
4. **Extensibility**: Easy to add new media types without breaking existing code

## Exercise: Extend the System

Try adding support for:
1. Playlist management
2. Different audio formats (MP3, WAV, etc.)
3. Network streaming with different protocols

Remember to maintain LSP compliance while adding these features!

## Coming Up in Chapter 5...
We'll explore advanced LSP concepts like covariance, contravariance, and how they affect your design decisions.