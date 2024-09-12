package com.example.zap

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.sample.zap.data.util.NetworkMonitor
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*

class NetworkMonitorTest {

    @Mock
    private lateinit var networkMonitor: NetworkMonitor
    @Mock
    private lateinit var mockContext: Context
    @Mock
    private lateinit var mockConnectivityManager: ConnectivityManager

    @Mock
    private lateinit var mockNetwork: Network
    @Mock
    private lateinit var mockCallback: NetworkMonitor.NetworkCallback

    @Before
    fun setup() {
        // Mock the context and connectivity manager
        mockContext = mock(Context::class.java)
        mockConnectivityManager = mock(ConnectivityManager::class.java)
        mockCallback = mock(NetworkMonitor.NetworkCallback::class.java)
        mockNetwork = mock(Network::class.java)

        // Return mocked ConnectivityManager when asked for it
        `when`(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(mockConnectivityManager)

        // Create an instance of NetworkMonitor with the mocked dependencies
        networkMonitor = NetworkMonitor(mockContext, mockCallback)
    }

    @Test
    fun `test onConnected method is called when network is available`() {
        // Capture the NetworkCallback passed to the ConnectivityManager
        val argumentCaptor = ArgumentCaptor.forClass(ConnectivityManager.NetworkCallback::class.java)

        // Register the network callback (this will trigger the captor)
        networkMonitor.register()

        // Verify that the ConnectivityManager's registerNetworkCallback method was called
        verify(mockConnectivityManager).registerNetworkCallback(any(NetworkRequest::class.java), argumentCaptor.capture())

        // Simulate network being available (trigger the onAvailable callback)
        argumentCaptor.value.onAvailable(mockNetwork)

        // Verify that the onConnected method was called on the callback
        verify(mockCallback).onConnected()
    }

    @Test
    fun testOnDisconnected() {

        // Capture the NetworkCallback passed to the ConnectivityManager
        val argumentCaptor = ArgumentCaptor.forClass(ConnectivityManager.NetworkCallback::class.java)

        // Mock the network callback
        networkMonitor.register()

        // Verify that the ConnectivityManager's registerNetworkCallback method was called
        verify(mockConnectivityManager).registerNetworkCallback(any(NetworkRequest::class.java), argumentCaptor.capture())

        // Simulate network being available (trigger the onAvailable callback)
        argumentCaptor.value.onLost(mockNetwork)


        // Verify that onDisconnected is called
        verify(mockCallback).onDisconnected()
    }
}
